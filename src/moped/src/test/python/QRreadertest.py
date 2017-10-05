import unittest
import cv2
import zbar

from PIL import Image

def decodeZbarData(zbar_image, trueDistanceFromCenter, imageCenter):

    # Prints data from image.
    if zbar_image is not None:
        for decoded in zbar_image:
            print(zbar_image)
            #saves The corners of the read QR-Code
            topLeftCorners, bottomLeftCorners, bottomRightCorners, topRightCorners = [item for item in decoded.location]
            #calculatesthe center fro m2 given xCoordinates
            centerPointQR = (bottomRightCorners[0] - bottomLeftCorners[0]) / 2
            #distanceFromCenter saves the distance given in pixels from the centerof the picture to the cetner of the QR-Code Postive = left of target
            distanceFromCenter =  centerPointQR - imageCenter
            #Prints the difference between center of the image and the center of the QR code
            print ("centerPoint :" + str(imageCenter) + ", centerPointQR: " + str(centerPointQR))
            print ("distance from center: " +str(distanceFromCenter))
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

    imageCenter = width/2
    decodeZbarData(zbar_image, trueDistanceFromCenter, imageCenter)

calculatecenter('testQR1.jpg', -129)
calculatecenter('testQR2.jpg', None)
calculatecenter('testQR3.jpg', -129)
