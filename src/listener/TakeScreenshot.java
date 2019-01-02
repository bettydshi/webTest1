package listener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import common.BaseFunction;

/**
 * ��ͼ.
 * 
 * @author betty.shi
 * 
 */
public class TakeScreenshot extends BaseFunction{
    

    public String takeScreenShot(String classname, String methodname) {
        // ��ȡ��ͼfile
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = getFilePath(classname, methodname);
        try {
            // ��ͼƬ�ƶ���ָ��λ��
            FileUtils.moveFile(file, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public String getFilePath(String classname, String methodname) {
        // ��������ͼƬ��·�����������򴴽�
        File dir = new File("test-output/report/screenshots");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String dateStr = dateFormat.format(new Date());
        // ��ȡ�µ��ļ���������ʱ�䣬������������
        String fileName = dateStr + "_" + classname + "_" + methodname + ".jpg";
        // ��ȡ�ļ�·��
        String filePath = dir.getAbsolutePath() + "/" + fileName;
        return filePath;

    }
}