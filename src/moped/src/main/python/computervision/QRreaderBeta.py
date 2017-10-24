import cv2
import zbar

from PIL import Image

def main():
    capture = cv2.VideoCapture(0)
    while True:
        ret, frame = capture.read()
    calculatecenter(frame)


def decodeZbarData(zbar_image, xCenter):
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
            return distanceFromCenter

def calculatecenter(filename):
    print('Testing ' + filename)
    gray = cv2.imread(filename, cv2.IMREAD_GRAYSCALE)
    img = Image.fromarray(gray)
    width, height = img.size
    zbar_image = zbar.Image(width, height, 'Y800', img.tobytes())
    scanner = zbar.ImageScanner()
    scanner.scan(zbar_image)

    xCenter = width/2
    return decodeZbarData(zbar_image, xCenter)



if __name__ == "__main__":
    main()