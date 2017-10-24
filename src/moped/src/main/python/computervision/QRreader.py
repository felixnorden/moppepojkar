import zbar
import pydoc

from PIL import Image
import cv2

"""This script reads a QRcode from a camera and outputs a relative ofset position
from the center of the screen."""

def main():
    # Begin capturing video. You can modify what video source to use with VideoCapture's argument. It's currently set
    # to be your webcam.

    capture = cv2.VideoCapture(0)
    while True:
        # To quit this program press q.
        #if cv2.waitKey(1) & 0xFF == ord('q'):
        #    break

        # Breaks down the video into frames
        ret, frame = capture.read()

        # Displays the current frame
        # cv2.imshow('MoppeWindow', frame)

        # Converts image to grayscale.
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        # Uses PIL to convert the grayscale image into a ndary array that ZBar can understand.
        image = Image.fromarray(gray)
        # Saves the width and height from the image
        width, height = image.size
        # converts the image to (for zbar) readable bytes
        zbar_image = zbar.Image(width, height, 'Y800', image.tobytes())

        # Scans the zbar image.
        scanner = zbar.ImageScanner()
        scanner.scan(zbar_image)

        #Get center point from image width
        centerPoint = width / 2
	
        # Prints data from image.
        for decoded in zbar_image:
            #print(decoded.data)

            #saves The corners of the read QR-Code
            topLeftCorners, bottomLeftCorners, bottomRightCorners, topRightCorners = [item for item in decoded.location]
            #calculatesthe center fro m2 given xCoordinates
            centerPointQR = (bottomLeftCorners[0] + bottomRightCorners[0]) / 2
            #distanceFromCenter saves the distance given in pixels from the centerof the picture to the cetner of the QR-Code Postive = left of target
            distanceFromCenter = centerPoint - centerPointQR
            #Prints the difference between center of the image and the center of the QR code
            print(-distanceFromCenter)
            '''   testkod nedan.
            print("Top left xy")
            print(topLeftCorners)
            print("Bottom left xy")
            print(bottomLeftCorners)
            print("Top right xy")
            print(topRightCorners)
            print("Bottom right xy")
            print(bottomRightCorners)
            '''


if __name__ == "__main__":
    main()

