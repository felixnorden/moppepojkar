import unittest
import cv2
import zbar


class QRreadertest:
    def calculatecenter:
        centerPoint = 200
        img = cv2.imread('testQR.jpg', cv2.IMREAD_GRAYSCALE)
        width, height = img.size
        zbar_image = zbar.Image(width, height, 'Y800', image.tobytes())
        scanner = zbar.ImageScanner()
        scanner.scan(zbar_image)

        # Prints data from image.
        for decoded in zbar_image:
            #saves The corners of the read QR-Code
            topLeftCorners, bottomLeftCorners, bottomRightCorners, topRightCorners = [item for item in decoded.location]
            #calculatesthe center fro m2 given xCoordinates
            centerPointQR = (bottomLeftCorners[0] + bottomRightCorners)[0] / 2
            #distanceFromCenter saves the distance given in pixels from the centerof the picture to the cetner of the QR-Code Postive = left of target
            distanceFromCenter = centerPoint - centerPointQR
            #Prints the difference between center of the image and the center of the QR code


            #print ("centerPoint :" + centerPoint + "centerPointQR: " + centerPointQR)
            assert distanceFromCenter == -55