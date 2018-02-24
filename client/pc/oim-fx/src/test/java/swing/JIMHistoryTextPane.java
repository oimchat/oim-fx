package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by dolphin on 15-03-05.
 */
public class JIMHistoryTextPane extends JIMSendTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConcurrentLinkedQueue<Message> messageConcurrentLinkedQueue;
	private ConcurrentHashMap<Integer, Image> senderHeadImageConcurrentHashMap;
	private Color otherMessageColor = new Color(188, 237, 245);
	private Color otherMessageBorderColor = new Color(156, 205, 213);
	private Color selfMessageColor = new Color(230, 230, 230);
	private Color selfMessageBorderColor = new Color(198, 198, 198);

	public JIMHistoryTextPane() {
		setEditable(false); // 用于显示历史消息，因此必须为只读模式，不允许用户修改内容
		setOpaque(false); // 设置成背景透明后，完全自绘才会看到效果
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 反锯齿平滑绘制

		// 通过对并发消息链表遍历绘制全部消息气泡、消息发出者头像
		if (messageConcurrentLinkedQueue != null) {
			Iterator<Message> iterator = messageConcurrentLinkedQueue.iterator();
			while (iterator.hasNext()) {
				Message message = iterator.next();

				Point point = message.getMessagePaintLeftTop();

				if (point != null) {
					// 绘制消息发出者头像
					if (senderHeadImageConcurrentHashMap != null) {
						Image image = senderHeadImageConcurrentHashMap.get(message.getSenderHeadImageID());
						if (image != null) {
							if (message.isSelfSender()) {
								g2D.drawImage(image, this.getWidth() - image.getWidth(null) - 9, point.y - 25, null);
							} else {
								// 消息发出者是别人，则头像靠左显示
								g2D.drawImage(image, 9, point.y - 25, null);
							}
						}
					}

					// 绘制额消息气泡左边小箭头
					int xPoints[] = new int[3];
					int yPoints[] = new int[3];

					if (message.isSelfSender()) {
						// 绘制自己消息圆角消息气泡矩形
						g2D.setColor(selfMessageColor);
						g2D.fillRoundRect(point.x - 7, point.y - 7, message.getMessagePaintWidth() + 14, message.getMessagePaintHeight() + 14, 10, 10);
						// 绘制圆角消息气泡边框
						g2D.setColor(selfMessageBorderColor);
						g2D.drawRoundRect(point.x - 7, point.y - 7, message.getMessagePaintWidth() + 14, message.getMessagePaintHeight() + 14, 10, 10);

						// 消息发出者是自己，则头像靠右显示
						xPoints[0] = (point.x - 7) + (message.getMessagePaintWidth() + 14);
						yPoints[0] = point.y;
						xPoints[1] = xPoints[0] + 7;
						yPoints[1] = point.y;
						xPoints[2] = xPoints[0];
						yPoints[2] = point.y + 7;

						g2D.setColor(selfMessageColor);
						g2D.fillPolygon(xPoints, yPoints, 3);
						g2D.setColor(selfMessageBorderColor);
						g2D.drawPolyline(xPoints, yPoints, 3);
						g2D.setColor(selfMessageColor);
						g2D.drawLine(xPoints[0], yPoints[0] + 1, xPoints[2], yPoints[2] - 1);
					} else {
						// 绘制别人消息圆角消息气泡矩形
						// 绘制圆角消息气泡矩形
						g2D.setColor(otherMessageColor);
						g2D.fillRoundRect(point.x - 7, point.y - 7, message.getMessagePaintWidth() + 14, message.getMessagePaintHeight() + 14, 10, 10);
						// 绘制圆角消息气泡边框
						g2D.setColor(otherMessageBorderColor);
						g2D.drawRoundRect(point.x - 7, point.y - 7, message.getMessagePaintWidth() + 14, message.getMessagePaintHeight() + 14, 10, 10);

						// 消息发出者是别人，则头像靠左显示
						xPoints[0] = point.x - 7;
						yPoints[0] = point.y;
						xPoints[1] = xPoints[0] - 7;
						yPoints[1] = point.y;
						xPoints[2] = xPoints[0];
						yPoints[2] = point.y + 7;

						g2D.setColor(otherMessageColor);
						g2D.fillPolygon(xPoints, yPoints, 3);
						g2D.setColor(otherMessageBorderColor);
						g2D.drawPolyline(xPoints, yPoints, 3);
						g2D.setColor(otherMessageColor);
						g2D.drawLine(xPoints[0], yPoints[0] + 1, xPoints[2], yPoints[2] - 1);
					}
				}
			} // while
		}

		super.paintComponent(g); // 执行默认组件绘制（消息文本、图片以及段落显示等内容）
	}

	public void setMessageConcurrentLinkedQueue(ConcurrentLinkedQueue<Message> messageConcurrentLinkedQueue) {
		this.messageConcurrentLinkedQueue = messageConcurrentLinkedQueue;
	}

	public void setSenderHeadImageConcurrentHashMap(ConcurrentHashMap<Integer, Image> senderHeadImageConcurrentHashMap) {
		this.senderHeadImageConcurrentHashMap = senderHeadImageConcurrentHashMap;
	}
}

