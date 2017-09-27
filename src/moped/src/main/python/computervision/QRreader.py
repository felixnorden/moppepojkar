import zbar

from PIL import Image
import cv2


def main():

    # Begin capturing video. You can modify what video source to use with VideoCapture's argument. It's currently set
    # to be your webcam.
    capture = cv2.VideoCapture(0)

    while True:
        # To quit this program press q.
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

        # Breaks down the video into frames
        ret, frame = capture.read()

        # Displays the current frame
        cv2.imshow('MoppeWindow', frame)

        # Converts image to grayscale.
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        # Uses PIL to convert the grayscale image into a ndary array that ZBar can understand.
        image = Image.fromarray(gray)
        width, height = image.size
        zbar_image = zbar.Image(width, height, 'Y800', image.tobytes())

        # Scans the zbar image.
        scanner = zbar.ImageScanner()
        scanner.scan(zbar_image)

        # Prints data from image.
        for decoded in zbar_image:
            print(decoded.data)
            topLeftCorners, bottomLeftCorners, bottomRightCorners, topRightCorners = [item for item in decoded.location]
            centerPointQR = (bottomLeftCorners[0] + bottomRightCorners)[0] / 2
            centerPoint = width/2
            #Postive = left of target
            distanceFromCenter = centerPoint - centerPointQR

            
            print("Top left xy")
            print(topLeftCorners)
            print("Bottom left xy")
            print(bottomLeftCorners)
            print("Top right xy")
            print(topRightCorners)
            print("Bottom right xy")
            print(bottomRightCorners)
            
            
            

    
        
if __name__ == "__main__":
    main()
