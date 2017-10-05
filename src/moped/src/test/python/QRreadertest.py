import unittest
import cv2
import zbar

from PIL import Image

def decodeZbarData(zbar_image, trueDistanceFromCenter, xCenter):
    centerPoint = xCenter
    # Prints data from image.
    if zbar_image is not None:
        for decoded in zbar_image:
            #saves The corners of the read QR-Code
            topLeftCorners, bottomLeftCorners, bottomRightCorners, topRightCorners = [item for item in decoded.location]
            #calculatesthe center fro m2 given xCoordinates
            centerPointQR = (bottomRightCorners[0] + bottomLeftCorners[0]) / 2
            #distanceFromCenter saves the distance given in pixels from the centerof the picture to the cetner of the QR-Code Postive = left of target
            distanceFromCenter = centerPoint - centerPointQR
            #Prints the difference between center of the image and the center of the QR code
            #print ("bottomRightCorners :" + str(bottomRightCorners[0]) + ", bottomLeftCorners: " + str(bottomLeftCorners[0]))
            assert trueDistanceFromCenter == distanceFromCenter
            print(trueDistanceFromCenter == distanceFromCenter)
    else:
        assert zbar_image == None
        print(zbar_image == None)

def calculatecenter(filename, trueDistanceFromCenter):
    print('Testing ' + filename)
    gray = cv2.imread(filename, cv2.IMREAD_GRAYSCALE)
    #cv2.imshow('TestWindow', gray)
    #cv2.waitKey(0)
    #cv2.destroyAllWindows()
    img = Image.fromarray(gray)
    width, height = img.size
    zbar_image = zbar.Image(width, height, 'Y800', img.tobytes())
    scanner = zbar.ImageScanner()
    scanner.scan(zbar_image)

    xCenter = width/2
    decodeZbarData(zbar_image, trueDistanceFromCenter, xCenter)

calculatecenter('testQR1.jpg', 89)
calculatecenter('testQR2.jpg', None)
calculatecenter('testQR3.jpg', -127)