set CAMURL=http://FRC:FRC@10.41.88.11/axis-cgi/jpg/image.cgi?resolution=320x240
REM java -cp build\AxisView.jar;SmartDashboard2013.jar PictureView http://75.76.84.146:88/erin_320x180.jpg 
REM java -cp build\AxisView.jar;SmartDashboard2013.jar PictureView http://FRC:FRC@10.41.88.11/axis-cgi/jpg/image.cgi?resolution=320x240
java -cp build\AxisView.jar;SmartDashboard2013.jar PictureView %CAMURL%
