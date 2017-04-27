package org.mule.modules.picamera.config;

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;

import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.display.Summary;
import org.mule.api.annotations.param.Optional;

import com.hopding.jrpicam.enums.*;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {

	@Configurable
	@Optional
	@Placement(order = 1, group = "Camera Options", tab = "General")
	private ImageEffect effect;
	
    /**
     * Set camera image effect
     *
     * @param the image effect
     */
    public void setEffect(ImageEffect effect) {
        this.effect = effect;
    }

    /**
     * Get camera image effect
     */
    public ImageEffect getEffect() {
        return this.effect;
    }   

}