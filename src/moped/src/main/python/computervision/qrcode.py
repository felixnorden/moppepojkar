from pyzbar.pyzbar import decode
from pyzbar.pyzbar import ZBarSymbol

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
        cv2.imshow('Current', frame)

        # Converts image to grayscale.
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        image = Image.fromarray(gray)

        qrcode = decode(gray, symbols=[ZBarSymbol.QRCODE])
        if(qrcode):
            print qrcode


if __name__ == "__main__":
    main()