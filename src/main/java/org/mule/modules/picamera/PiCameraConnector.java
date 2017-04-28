package org.mule.modules.picamera;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.display.Summary;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.picamera.config.ConnectorConfig;
import org.mule.util.FileUtils;

import com.hopding.jrpicam.*;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import com.hopding.jrpicam.enums.AWB;
import com.hopding.jrpicam.enums.DRC;
import com.hopding.jrpicam.enums.Encoding;
import com.hopding.jrpicam.enums.Exposure;
import com.hopding.jrpicam.enums.ImageEffect;
import com.hopding.jrpicam.enums.MeteringMode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

@Connector(name="pi-camera", friendlyName="PiCamera", description = "Raspberry Pi Camera")
public class PiCameraConnector {

    @Config
    ConnectorConfig config;

    /**
     * Take Picture to File processor
     *
     * @param fileName Name to save file as
     * @param path Folder location to save file to
     * @return File of picture taken
     * @throws FailedToRunRaspistillException 
     */
    @Processor
    @Summary("Take Picture to File")
    public File takePictureToFile(@Default("IMG_PI") String fileName, @Default("/home/pi/Pictures") String path, @Default("JPG") final Encoding encoding){
    	ByteArrayOutputStream os = null; 
    	RPiCamera piCamera;
    	File image = null;
		String newFileName = fileName + "_1." + encoding.toString();
		
		try {
			piCamera = new RPiCamera(path);
			
			piCamera.setWidth(320).setHeight(240);
			piCamera.enableBurst();
			
			// Check to see if file already exists
			image = new File(path + "/" + newFileName);			
			for (int i = 1; image.exists(); i++) {
				newFileName = String.format(fileName + "_%d." + encoding.toString(), i);
				image = new File(path + "/" + newFileName);				
			}
						
			image = piCamera.takeStill(newFileName);
			
		} catch (FailedToRunRaspistillException e) {
			
			InputStream inputStream = null;
			OutputStream outputStream = null;
			
			try {
				// read this file into InputStream
				inputStream = getClass().getResourceAsStream("/IMG_PI_1.png");	
				image = new File(path + "/" + fileName + "_1.png");
						
				for (int i = 1; image.exists(); i++) {
				    image = new File(path + "/" + String.format(fileName + "_%d.png", i));
				}
				
				// write the inputStream to a FileOutputStream
				outputStream =
		                    new FileOutputStream(image);

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				return image;

			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
				if (outputStream != null) {
					try {
						// outputStream.flush();
						outputStream.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}

				}
			}
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return image;
    }
    
    /**
     * Take Picture processor
     *
     * @return Byte array of picture taken
     * @throws FailedToRunRaspistillException 
     */
    @Processor
    @Summary("Take Picture")
    public byte[] takePicture(@Default("JPG") final Encoding encoding) {
    	
    	ByteArrayOutputStream os = new ByteArrayOutputStream();    	
    	
    	try {
    		BufferedImage buffImage = null;
    		
    		RPiCamera piCamera = new RPiCamera();
    		
    		piCamera.setWidth(320).setHeight(240);
    		piCamera.enableBurst();
        	
        	if (config.getEffect() != null){
        		piCamera.setImageEffect(config.getEffect());
        	}    
    		
        	buffImage = piCamera.takeBufferedStill();
			
			ImageIO.write(buffImage, encoding.toString(), os);
    		
		} catch(FailedToRunRaspistillException e) {
			e.printStackTrace();
			
			BufferedImage defaultImage;
			try {
				defaultImage = ImageIO.read(getClass().getResourceAsStream("/IMG_PI_1.png"));
				ImageIO.write(defaultImage, "png", os);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	return os.toByteArray();
    }
    
    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

}