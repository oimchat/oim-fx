/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.swing.common.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * @author Hero
 */
public class ImageIconUtil {

	public static Image getImagePath(String path) {
		return new ImageIcon(path).getImage();
	}

	public static Image getImagePath(String path, int w, int h) {
		Image image = getImagePath(path);
		if (null != image) {
			image = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		}
		return image;
	}

	public static Image getRoundedCornerImage(String imagePath, int width, int height, int cornersWidth, int cornerHeight) {
		Image image = getRoundedCornerBufferedImage(imagePath, width, height, cornersWidth, cornerHeight);
		return image;
	}

	public static BufferedImage getRoundedCornerBufferedImage(String imagePath, int width, int height, int cornersWidth, int cornerHeight) {
		try {
			File imageFile = new File(imagePath);
			if (imageFile.exists()) {
				BufferedImage image = ImageIO.read(imageFile); // BufferedImage)
				// Toolkit.getDefaultToolkit().getImage(imagePath);

				int w = image.getWidth();
				int h = image.getHeight();
				if (0 != width && 0 < width) {
					w = width;
				}
				if (0 != height && 0 < height) {
					h = height;
				}
				BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2 = output.createGraphics();
				g2.setComposite(AlphaComposite.Src);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				// g2.setColor(new Color(0,0,0));
				// g2.setBackground(Color);
				g2.setStroke(new BasicStroke(1));
				g2.fillRoundRect(0, 0, w, h, cornersWidth, cornerHeight);
				// g2.setComposite(AlphaComposite.Src);
				// g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornersWidth,
				// cornerHeight));
				g2.setComposite(AlphaComposite.SrcAtop);
				// g2.setColor(Color.white);//这里设置背景颜色
				// g2.fillRect(0, 0, w, h);//这里填充背景颜色
				g2.drawImage(image, 0, 0, w, h, null);
				g2.dispose();
				return output;
			}
		} catch (IOException ex) {
			Logger.getLogger(ImageIconUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static Image getRoundedCornerImage(Image image, int width, int height, int cornersWidth, int cornerHeight) {
		return getRoundedCornerBufferedImage(imageToBufferImage(image), width, height, cornersWidth, cornerHeight);
	}

	public static BufferedImage getRoundedCornerBufferedImage(BufferedImage image, int width, int height, int cornersWidth, int cornerHeight) {
		int w = image.getWidth();
		int h = image.getHeight();
		if (0 != width && 0 < width) {
			w = width;
		}
		if (0 != height && 0 < height) {
			h = height;
		}
		BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = output.createGraphics();
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// g2.setColor(new Color(0,0,0));
		// g2.setBackground(Color);
		g2.setStroke(new BasicStroke(1));
		g2.fillRoundRect(0, 0, w, h, cornersWidth, cornerHeight);
		// g2.setComposite(AlphaComposite.Src);
		// g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornersWidth,
		// cornerHeight));
		g2.setComposite(AlphaComposite.SrcAtop);
		// g2.setColor(Color.white);//这里设置背景颜色
		// g2.fillRect(0, 0, w, h);//这里填充背景颜色
		g2.drawImage(image, 0, 0, w, h, null);
		g2.dispose();
		return output;
	}

	public static BufferedImage imageToBufferImage(Image image) {
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// int transparency = Transparency.OPAQUE;
			int transparency = Transparency.TRANSLUCENT;
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			return bimage;
		}
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}
		// Graphics g = bimage.createGraphics();
		// g.drawImage(image, 0, 0, null);
		// g.dispose();
		return bimage;
	}

	public static BufferedImage getBufferedImage(String image, int alpha) {
		if (null != image && !"".equals(image)) {

			BufferedImage bufferedImage = null;
			FileInputStream stream = null;
			try {
				// 读取图片
				stream = new FileInputStream(new File(image));// 指定要读取的图片
				ByteArrayOutputStream os = new ByteArrayOutputStream();// 定义一个字节数组输出流，用于转换数组
				byte[] data = new byte[1024];// 定义一个1K大小的数组
				while (stream.read(data) != -1) {
					os.write(data);
				}
				ImageIcon imageIcon = new ImageIcon(os.toByteArray());
				bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
				g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

				if (alpha < 0) { // 判读透明度是否越界
					alpha = 0;
				} else if (alpha > 10) {
					alpha = 10;
				}
				// 循环每一个像素点，改变像素点的Alpha值
				for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
					for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
						int rgb = bufferedImage.getRGB(j2, j1);
						rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);
						bufferedImage.setRGB(j2, j1, rgb);
					}
				}
				g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
				// 生成图片为PNG
			} catch (Exception ex) {
				Logger.getLogger(ImageIconUtil.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				if (null != stream) {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bufferedImage;
		} else {
			return null;
		}
	}

	// //////////////
	public static BufferedImage getEmptyBufferedImage(int w, int h) {
		BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		return bufferedImage;
	}

	public static Image getEmptyImage(int w, int h) {
		return getEmptyBufferedImage(w, h);
	}
}
