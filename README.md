# Raspberry Pi Camera Anypoint Connector

This is a MuleSoft Anypoint Connector for the Raspberry Pi Camera. It leverages the [`JRPiCam`](https://github.com/Hopding/JRPiCam) API and provides access to the Raspberry Pi Camera. The JRPiCam library wraps and runs the native `raspistill` program on the Raspberry Pi.

Because the Connector works by invoking the `raspistill` software, it is important that your RPi be properly configured to run `raspistill`. The appropriate settings may be configured by running `raspi-config` in the terminal of your RPi. Further instructions can be found [here](https://www.raspberrypi.org/documentation/configuration/camera.md).

# Mule supported versions
Mule 3.8.x

# Supported versions
Raspberry Pi Camera

# Service or application supported modules
Raspberry Pi Camera


# Installation
For beta connectors you can download the source code and build it with the Anypoint DevKit to find it available on your local repository. Then you can add it to Studio

For released connectors you can download them from the update site in Anypoint Studio.
Open Anypoint Studio, go to Help → Install New Software and select Anypoint Connectors Update Site where you’ll find all available connectors.

# Usage
For information about usage our documentation at http://github.com/djuang1/pi-camera-connector.

# Reporting Issues

We use GitHub:Issues for tracking issues with this connector. You can report new issues at this link http://github.com/djuang1/pi-camera-connector/issues.
