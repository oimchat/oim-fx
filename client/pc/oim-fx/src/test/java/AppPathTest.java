import java.io.File;

/**
 * @author XiaHui
 * @date 2017年9月28日 下午3:03:57
 */
public class AppPathTest {

	public static void main(String[] a) {
		String path = System.getProperty("user.dir");
		String app = System.getProperty("app.dir");
		System.out.println(path);
		System.out.println(app);
		
        if (new File(path + File.separator + "resources").exists()) {
            System.setProperty("app.dir", new File(path).getAbsolutePath());
        } else {
            // 去掉了最后一个main目录
            path = path.substring(0, path.lastIndexOf(File.separator));
            System.out.println(path);
            System.setProperty("app.dir", new File(path).getAbsolutePath());
        }
        app = System.getProperty("app.dir");
        System.out.println(app);
	}
}
