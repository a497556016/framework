package com.framework.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.dao.ChatMessageMapper;
import com.framework.model.ChatMessage;
import com.framework.servlet.ServletContextManager;
import com.framework.shiro.dao.UserLoginMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class WebSocketServer {
	private Gson gson = new Gson();
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private static Map<String, WebSocketServer> webSocketMap = new HashMap<>();
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    private String fromPersonCode;
    private String fromLastName;
    

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        this.fromPersonCode = getParameter(session,"personCode");
        this.fromLastName = getParameter(session, "lastName");
        webSocketMap.put(this.fromPersonCode, this);
//        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
    	webSocketMap.remove(this.fromPersonCode);
//        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
       
        RequestMessage requestMessage = this.decode(message);
        
        ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setDateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		responseMessage.setMessage(requestMessage.getMessage());
		responseMessage.setPersonCode(this.fromPersonCode);
		responseMessage.setLastName(this.fromLastName);
		//保存消息
		ChatMessageMapper chatMessageMapper = (ChatMessageMapper) ServletContextManager.getWebApplicationContext().getBean("chatMessageMapper");
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMessage(requestMessage.getMessage());
		chatMessage.setCreateBy(this.fromPersonCode);
		chatMessage.setCreateDate(new Date());
		chatMessage.setPersonCode(requestMessage.getToPersonCode());
		chatMessageMapper.insert(chatMessage);
		responseMessage.setMessageId(chatMessage.getId());
		
        WebSocketServer selfServer = webSocketMap.get(this.fromPersonCode);
        if(null!=requestMessage.getToPersonCode()){
        	WebSocketServer server = webSocketMap.get(requestMessage.getToPersonCode());
        	try {
        		if(null==server){
        			responseMessage.setMessage("我下线了，下次再聊。");
        			responseMessage.setPersonCode(requestMessage.getToPersonCode());
        			selfServer.sendMessage(gson.toJson(responseMessage));
        		}else{
        			server.sendMessage(gson.toJson(responseMessage));
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }else{
	        //群发消息
	        for(String item: webSocketMap.keySet()){
	            try {
	            	webSocketMap.get(item).sendMessage(gson.toJson(responseMessage));
	            } catch (IOException e) {
	                e.printStackTrace();
	                continue;
	            }
	        }
        }
        
    }

    private RequestMessage decode(String message) {
    	RequestMessage requestMessage = new RequestMessage();
    	try {
			requestMessage = gson.fromJson(message, RequestMessage.class);
		} catch (JsonSyntaxException e) {
			requestMessage.setMessage(message);
		}
		return requestMessage;
	}

	/**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
    
    private String getParameter(Session session,String key){
    	Map<String, List<String>> requestMap = session.getRequestParameterMap();
        List<String> personCodes = requestMap.get(key);
        if(CollectionUtils.isNotEmpty(personCodes)){
        	String personCode = personCodes.get(0);
        	return personCode;
        }
        return null;
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}