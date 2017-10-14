package com.framework.util;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import sun.font.FontDesignMetrics;
import sun.misc.BASE64Decoder;

public class ImageUtils {
	/**
	 * 图片压缩
	 * @param file 源文件
	 * @param resize 压缩后的文件大小
	 * @return
	 */
	public static boolean resizeImage(File file,long resize){
		String filePath = file.getPath();
		BufferedImage src = null;
		FileOutputStream out = null;
		ImageWriter imgWrier;
		ImageWriteParam imgWriteParams;
		
		// 指定写图片的方式为 jpg
		
		imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
		imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
		// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
		imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		// 这里指定压缩的程度，参数qality是取值0~1范围内，
		imgWriteParams.setCompressionQuality(((float)resize/(float)file.length()));
		imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

		
		try {
			BufferedImage image = ImageIO.read(file);
			if(null==image){
				
				return false;
			}
			ColorModel colorModel = image.getColorModel();// ColorModel.getRGBdefault();

			//ColorModel colorModel = ColorModel.getRGBdefault();

			// 指定压缩时使用的色彩模式
			// imgWriteParams.setDestinationType(new
			// javax.imageio.ImageTypeSpecifier(
			// colorModel, colorModel.createCompatibleSampleModel(16, 16)));
			imgWriteParams.setDestinationType(
			new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));

			System.out.println(file.length());
			src = ImageIO.read(file);
			out = new FileOutputStream(filePath);
	
			// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
			// OutputStream构造
			imgWrier.setOutput(ImageIO.createImageOutputStream(out));
			// 调用write方法，就可以向输入流写图片
	
			imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);
	
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 按宽高缩放图片文件，保存文件到指定路径
	 * @param oldUrl
	 * @param newUrl
	 * @param width
	 * @param height
	 */
	public static void resizeImage(String oldUrl, String newUrl, int width, int height) {
		try {
			String[] names = newUrl.split("\\.");
			File original = new File(oldUrl);
			File newImg = new File(newUrl);
			Image originalImg = ImageIO.read(original);
			Image newImage = getScaledImage(originalImg, width, height);
			BufferedImage buff = (BufferedImage) newImage;
			FileOutputStream fileOutputStream = FileUtils.openOutputStream(newImg);
			ImageIO.write(buff, names[names.length-1], fileOutputStream);
			
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按高缩放图片文件，保存文件到指定路径
	 * @param original
	 * @param newUrl
	 * @param format
	 * @param height
	 */
	public static void resizeImageByHeight(File original, String newUrl, String format, int height) {
		try {
			File newImg = new File(newUrl);
			Image originalImg = ImageIO.read(original);
			int width = height * originalImg.getWidth(null) / originalImg.getHeight(null);
			Image newImage = getScaledImage(originalImg, width, height);
			BufferedImage buff = (BufferedImage) newImage;
			ImageIO.write(buff, format, newImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按宽缩放图片文件，保存文件到指定路径
	 * @param original
	 * @param newUrl
	 * @param format
	 * @param height
	 */
	public static void resizeImageByWidth(File original, String newUrl, String format, int width) {
		try {
			File newImg = new File(newUrl);
			Image originalImg = ImageIO.read(original);
			int height = width * originalImg.getHeight(null) / originalImg.getWidth(null);
			Image newImage = getScaledImage(originalImg, width, height);
			BufferedImage buff = (BufferedImage) newImage;
			ImageIO.write(buff, format, newImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按宽高缩放图片文件
	 * @param srcImg
	 * @param w
	 * @param h
	 * @return
	 */
	public static Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	public static int getWidth(File img) {
		int width = 0;
		try {
			BufferedImage originalImg = ImageIO.read(img);
			width = originalImg.getWidth();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return width;
	}

	public static int getHeight(File img) {
		int height = 0;
		try {
			BufferedImage originalImg = ImageIO.read(img);
			height = originalImg.getHeight();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return height;
	}
	
	/**
	 * // 对字节数组字符串进行Base64解码并生成图片
	 * @param imgStr
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public static boolean generateImage(String imgStr, String filePathName) throws Exception {
		if(StringUtils.isBlank(imgStr)) throw new Exception("传进来的图片编码不能为空!!");
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(filePathName);
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 根据源图片大小，生成带有多行文本水印的图片
	 * @param 
	 * 		  srcPath 需要加水印的图片地址
	 * @param 
	 * 		  targetPath 生成带水印图片的地址
	 * @param 
	 * 		  font 水印文字设置
 	 * @param 
 	 * 		  color 水印文字颜色
 	 * @param 
 	 * 		  option 自定义水印参数
	 * @param 
	 * 		  params 水印文字列表
	 * @author 
	 * 		  hzj
	 */
	public static void markWaterMakImg(String srcPath, String targetPath,
			ImageOption option, String... params) {
		try {
			//默认宽度,和默认字体
			Font font = new Font("微软雅黑", option.getFontStyle(), option.getFontSize());
			int width = option.getMarkWaterPicWidth();
			
			FontDesignMetrics fm = FontDesignMetrics.getMetrics(font);
			List<String> strAllList = new ArrayList<String>();
			int height = fm.getHeight();
			
			
			if (ArrayUtils.isNotEmpty(params)) {
				for (String text : params) {
					//将文本拆为多行
					generateStringList(text, width, strAllList, fm);
				}
			}
			
			//1.jpg是你的 主图片的路径
	        InputStream is = new FileInputStream(srcPath);
	        
	        //通过JPEG图象流创建JPEG数据流解码器
	        JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
	        //解码当前JPEG数据流，返回BufferedImage对象
	        BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
	        int srcWidth = buffImg.getWidth();
	        int srcHeight = buffImg.getHeight();
	        //得到画笔对象
	        Graphics2D g = buffImg.createGraphics();
			
			g.setColor(option.getColor());
			g.setFont(font);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					0.6f));

			//在水印图片上输入文字
			int totalHeight = strAllList.size()*(height+10);
			for (int i = 0; i < strAllList.size(); i++) {
				g.drawString(strAllList.get(i), srcWidth-width, (srcHeight-totalHeight)+(i + 1) * height);
			}
			g.dispose();
			
			
			OutputStream os = FileUtils.openOutputStream(new File(targetPath));
		        
	        //创键编码器，用于编码内存中的图象数据。
	        
	        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
	        en.encode(buffImg);
	        
	        is.close();
	        os.flush();
	        os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * 根据规定宽度将一段文字换行
	 * @param text  指定文字
	 * @param width 规定宽度
	 * @param strList 换行后的文字列表
	 * @param fm  字体管理功能
	 */
	public static void generateStringList(String text, int width,
			List<String> strList, FontDesignMetrics fm) {

		if (compare(text, width, fm) == -1) {
			strList.add(text);
			return;
		} else {
			int charNum = 0;
			int charLen = 0;

			for (char c : text.toCharArray()) {
				charNum++;
				charLen += fm.charWidth(c);
				if (charLen > width) {
					charLen = charLen - fm.charWidth(c);
					charNum--;
					break;
				} else if (charLen == width) {
					break;
				}
			}
			strList.add(text.substring(0, charNum));
			generateStringList(text.substring(charNum), width,
					strList, fm);

		}

		return;
	}
	/**
	 * 比较文字的长度和规定宽度
	 * @param text 指定文字
	 * @param width 规定宽度
	 * @param fm   文字信息工具
	 * @return 返回-1表示比规定宽度短；0表示等于规定宽度；1表示大于规定宽度
	 */
	public static int compare(String text, int width, 
			FontDesignMetrics fm) {

		int textLen = fm.stringWidth(text);
		if (width > textLen) {
			return -1;
		} else if (width == textLen) {
			return 0;
		} else {
			return 1;
		}
	}
}
