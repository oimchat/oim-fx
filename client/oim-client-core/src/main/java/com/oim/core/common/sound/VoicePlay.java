package com.oim.core.common.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * @author XiaHui
 * @date 2017年8月28日 下午5:39:44
 */
public class VoicePlay {

	boolean playStart = false;
	boolean listenStart = false;
	int length = 0;
	private TargetDataLine listenLine;// 管道
	private AudioFormat format;
	private SourceDataLine playLine;
	private boolean lineSupported = false;

	public VoicePlay() {

		try {

			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);
			DataLine.Info listenInfo = new DataLine.Info(TargetDataLine.class, format);
			boolean l = AudioSystem.isLineSupported(listenInfo);
			if (l) {
				listenLine = (TargetDataLine) AudioSystem.getLine(listenInfo);
			}

			DataLine.Info playInfo = new DataLine.Info(SourceDataLine.class, format);
			boolean p = AudioSystem.isLineSupported(listenInfo);
			if (p) {
				playLine = (SourceDataLine) AudioSystem.getLine(playInfo);
			}
			lineSupported = l && p;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public boolean startListen() {
		if (null != listenLine && lineSupported) {
			try {
				listenLine.open(format, listenLine.getBufferSize());
				listenLine.start();
				length = (int) (format.getFrameSize() * format.getFrameRate() / 2.0f);
				listenStart = true;
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				listenStart = false;
			}
		}
		return listenStart;
	}

	public boolean startPlay() {
		if (null != playLine && lineSupported) {
			try {

				playLine.open(format);
				playLine.start();
				playStart = true;
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				playStart = false;
			}
		}
		return playStart;
	}

	public boolean isPlayStart() {
		return playStart;
	}

	public void closePlay() {
		if (null != playLine) {
			// playLine.drain();
			playLine.stop();
			// playLine.close();
			playStart = false;
		}
	}

	public boolean isListenStart() {
		return listenStart;
	}

	public void closeListen() {
		if (null != listenLine) {
			// listenLine.drain();
			listenLine.stop();
			// listenLine.close();
			listenStart = false;
		}
	}

	public byte[] getVoice() {
		byte[] data = new byte[length];
		// boolean isRunning=listenLine.isRunning();
		if (null != listenLine && listenStart) {
			listenLine.read(data, 0, length);
		}
		return data;
	}

	public void playVoice(byte[] bytes) {
		playVoice(bytes, 0);
	}

	public void playVoice(byte[] bytes, float value) {
		if (null != playLine) {
			FloatControl control = (FloatControl) playLine.getControl(FloatControl.Type.MASTER_GAIN);
			float size = 0;

			float minimum = control.getMinimum();
			float maximum = control.getMaximum();
			float all = maximum - minimum;
			if (value <= 0) {
				size = minimum;
			} else if (value >= 100) {
				size = maximum;
			} else {
				float i = all / 100F;
				size = (i * value) + minimum;
			}
			control.setValue(size);// newVal - the value of volume slider
			playLine.write(bytes, 0, bytes.length);
		}
	}
}
