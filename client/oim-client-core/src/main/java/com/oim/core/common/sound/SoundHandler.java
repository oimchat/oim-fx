package com.oim.core.common.sound;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XiaHui
 * @date 2015年3月10日 上午10:51:30
 */
public class SoundHandler {
	public static final int sound_type_message = 1;
	public static final int sound_type_system = 2;
	public static final int sound_type_shake = 3;
	public static final int sound_type_status = 4;
	public static final int sound_type_audio = 5;
	public static final int sound_type_video = 6;
	private SoundPlay soundPlay = new SoundPlay();
	private Map<Integer, File> map = new HashMap<Integer, File>();
	private Map<Integer, Long> timeMap = new HashMap<Integer, Long>();
	
	public SoundHandler(){
		map.put(sound_type_message, new File("Resources/Sound/wav/msg.wav"));
		map.put(sound_type_system, new File("Resources/Sound/wav/system.wav"));
		map.put(sound_type_shake, new File("Resources/Sound/wav/shake.wav"));
		map.put(sound_type_status, new File("Resources/Sound/wav/Global.wav"));
		map.put(sound_type_audio, new File("Resources/Sound/wav/Audio.wav"));
		map.put(sound_type_video, new File("Resources/Sound/wav/Alert.wav"));
	}
	
	public void play(int type){
		Long time=timeMap.get(type);
		if(null==time||((System.currentTimeMillis()-time)>1500)){
			File file=map.get(type);
			if(null!=file&&file.exists()){
				soundPlay.play(file);
			}
			timeMap.put(type, System.currentTimeMillis());
		}
	}
}
