import unittest
import cv2
import zbar

from PIL import Image

def calculatecenter():
    centerPoint = 200
    gray = cv2.imread('testQR.jpg', cv2.IMREAD_GRAYSCALE)
    #cv2.imshow('TestWindow', gray)
    #cv2.waitKey(0)
    #cv2.destroyAllWindows()
    img = Image.fromarray(gray)
    width, height = img.size
    zbar_image = zbar.Image(width, height, 'Y800', img.tobytes())
    scanner = zbar.ImageScanner()
    scanner.scan(zbar_image)

    print('Zbar data dump: ')
    print(zbar_image)

    # Prints data from image.
    for decoded in zbar_image:
        print(zbar_image)
        #saves The corners of the read QR-Code
        topLeftCorners, bottomLeftCorners, bottomRightCorners, topRightCorners = [item for item in decoded.location]
        #calculatesthe center fro m2 given xCoordinates
        centerPointQR = (bottomRightCorners[0] - bottomLeftCorners[0]) / 2
        #distanceFromCenter saves the distance given in pixels from the centerof the picture to the cetner of the QR-Code Postive = left of target
        distanceFromCenter = centerPoint - centerPointQR
        #Prints the difference between center of the image and the center of the QR code
        print ("centerPoint :" + str(centerPoint) + "centerPointQR: " + str(centerPointQR))
        assert distanceFromCenter == -55

calculatecenter()
