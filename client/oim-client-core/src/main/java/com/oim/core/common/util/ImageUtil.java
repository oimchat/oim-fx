/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.common.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 
 * @author Hero
 */
public class ImageUtil {

	public static ImageIcon getImageIconByPath(String imagePath, int w, int h) {
		ImageIcon imageIcon = null;
		if (!"".equals(imagePath)) {
			imageIcon = getImageIcon(imagePath);
			if (null != imageIcon) {
				int width = imageIcon.getIconWidth();
				int height = imageIcon.getIconHeight();
				float xbili = (float) ((w * 1.0) / width);
				float ybili = (float) ((h * 1.0) / height);
				float bili = xbili > ybili ? ybili : xbili;
				int newwidth = (int) (width * bili);
				int newheight = (int) (height * bili);
				Image image = getImage(imagePath, newwidth, newheight);
				imageIcon.setImage(image);
			}

		}

		return imageIcon;
	}

	public static Image getImage(String path) {
		return Toolkit.getDefaultToolkit().getImage(path);
	}

	public static Image getImage(String path, int w, int h) {
		Image image = getImage(path);
		if (null != image) {
			image = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		}
		return image;
	}

	public static Icon getIcon(String path) {
		return getImageIcon(path);
	}

	public static Icon getIcon(String path, int w, int h) {
		return getImageIcon(path, w, h);
	}

	public static ImageIcon getImageIcon(String path) {
		Image image = getImage(path);
		if (null != image) {
			return new ImageIcon(image);
		}
		return null;
	}

	public static ImageIcon getImageIcon(String path, int w, int h) {
		Image image = getImage(path, w, h);
		if (null != image) {
			return new ImageIcon(image);
		}
		return null;
	}

	public static Image getRoundedCornerImage(String imagePath, int width, int height, int cornersWidth, int cornerHeight) {
		Image image = getRoundedCornerBufferedImage(imagePath, width, height, cornersWidth, cornerHeight);
		return image;
	}

	public static Icon getRoundedCornerIcon(String imagePath, int width, int height, int cornersWidth, int cornerHeight) {
		return getRoundedCornerImageIcon(imagePath, width, height, cornersWidth, cornerHeight);
	}

	public static ImageIcon getRoundedCornerImageIcon(String imagePath, int width, int height, int cornersWidth, int cornerHeight) {
		BufferedImage image = getRoundedCornerBufferedImage(imagePath, width, height, cornersWidth, cornerHeight);
		if (null == image) {
			return null;
		}
		ImageIcon icon = new ImageIcon();
		icon.setImage(image);
		return icon;
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
			Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static Image getRoundedCornerImage(Image image, int width, int height, int cornersWidth, int cornerHeight) {
		return getRoundedCornerBufferedImage(imageToBufferImage(image), width, height, cornersWidth, cornerHeight);
	}

	public static Icon getRoundedCornerIcon(Image image, int width, int height, int cornersWidth, int cornerHeight) {
		return getRoundedCornerImageIcon(imageToBufferImage(image), width, height, cornersWidth, cornerHeight);
	}

	public static ImageIcon getRoundedCornerImageIcon(Image image, int width, int height, int cornersWidth, int cornerHeight) {
		BufferedImage bufferedImage = getRoundedCornerBufferedImage(imageToBufferImage(image), width, height, cornersWidth, cornerHeight);
		if (null == bufferedImage) {
			return null;
		}
		ImageIcon icon = new ImageIcon();
		icon.setImage(bufferedImage);
		return icon;
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
			FileInputStream stream =null;
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
				Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
			}finally{
				if(null!=stream){
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

	public static Image getImage(Image image, int w, int h) {
		return image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	}

	public static Icon getIcon(Image image, int w, int h) {
		image = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	public static ImageIcon getImageIcon(Image image, int w, int h) {
		image = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}

	public static BufferedImage getRoundedCornerBufferedImage(int rx, int ry, Image image) {
		Shape shape = new RoundRectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null), rx, ry);
		BufferedImage cutImage = getRoundedCornerBufferedImage(shape, image);
		return cutImage;
	}

	public static BufferedImage getRoundedCornerBufferedImage(Shape shape, Image image) {

		BufferedImage cutImage = imageToBufferImage(image);

		Graphics2D g2d = cutImage.createGraphics();
		g2d.setPaint(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fill(shape);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));

		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		return cutImage;
	}

	// //////////////
	public static BufferedImage getEmptyBufferedImage(int w, int h) {
		BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		return bufferedImage;
	}

	public static Image getEmptyImage(int w, int h) {
		return getEmptyBufferedImage(w, h);
	}

	public static ImageIcon getEmptyImageIcon(int w, int h) {
		BufferedImage bufferedImage = getEmptyBufferedImage(w, h);
		return new ImageIcon(bufferedImage);
	}

	public static Icon getEmptyIcon(int w, int h) {
		BufferedImage bufferedImage = getEmptyBufferedImage(w, h);
		return new ImageIcon(bufferedImage);
	}

	// ////////////////////////////////////////////////////////////////////////
	private static final String PICTRUE_FORMATE_JPG = "jpg";

	/**
	 * 添加图片水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg
	 *            水印图片路径，如：C://myPictrue//logo.png
	 * @param x
	 *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间 @ param y
	 *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间 @ param alpha 透明度(0.0 -- 1.0,
	 *            0.0为完全透明，1.0为完全不透明)
	 */
	public static void pressImage(String targetImg, String waterImg, int x, int y, float alpha) {
		try {
			File file = new File(targetImg);
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);

			Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}
			g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
			g.dispose();
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加文字水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间 @ param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间 @ param alpha 透明度(0.0 -- 1.0,
	 *            0.0为完全透明，1.0为完全不透明)
	 */
	public static void pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
		try {
			File file = new File(targetImg);

			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			int width_1 = fontSize * getLength(pressText);
			int height_1 = fontSize;
			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}

			g.drawString(pressText, x, y + height_1);
			g.dispose();
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 * 
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	 */
	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}

	/**
	 * 图片缩放
	 * 
	 * @param filePath
	 *            图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 */
	public static void resize(String filePath, int height, int width, boolean bb) {
		try {
			double ratio = 0; // 缩放比例
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null)) {
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
				} else {
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
				}
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "jpg", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 模糊算法（已优化）
	public static ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be >= 1");
		}

		int size = radius * 2 + 1;
		float[] data = new float[size];

		float sigma = radius / 3.0f;
		float twoSigmaSquare = 2.0f * sigma * sigma;
		float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
		float total = 0.0f;

		for (int i = -radius; i <= radius; i++) {
			float distance = i * i;
			int index = i + radius;
			data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
			total += data[index];
		}

		for (int i = 0; i < data.length; i++) {
			data[i] /= total;
		}

		Kernel kernel = null;
		if (horizontal) {
			kernel = new Kernel(size, 1, data);
		} else {
			kernel = new Kernel(1, size, data);
		}
		return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
	}

	public static String getFileExtractor(String fileName) {
		String[] strs = fileName.replace(".", ",").split(",");
		return strs[strs.length - 1];
	}

	public static void main(String[] args) throws IOException {
		// pressImage("C://pic//jpg", "C://pic//test.gif", 5000, 5000, 0f);
		// pressText("C://xcd.jpg", "2012-07-17", "宋体", Font.BOLD, 12,
		// Color.BLACK, 307, 69, 1.0F);
		// resize("C://pic//4.jpg", 1000, 500, true);
	}
}
