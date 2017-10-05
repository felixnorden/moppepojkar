import cv2
import numpy as np
cap = cv2.VideoCapture(0)
while(1):

    # Take each frame
    _, frame = cap.read()

    # Convert BGR to HSV

    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    # define range of blue color in HSV 
    lower_green = np.array([50,50,120]) 
    upper_green = np.array([70,255,255])

    imgThreshHigh = cv2.inRange(hsv, lower_green, upper_green)

    imgray = cv2.cvtColor(frame,cv2.COLOR_BGR2GRAY)
    thresh = 18
    edges = cv2.Canny(imgray,thresh,thresh*3)

    circles = cv2.HoughCircles(imgThreshHigh, cv2.HOUGH_GRADIENT, 1, 500, 25, 75, 5, 15)
    maxRadius= 0
    xc = 0.00
    yc = 0.00
    found = False 
    if circles is not None:
        found = True
        for i in circles[0,:3]:
            if i[2] < 100:
                if i[2] > maxRadius:
                    maxRadius = i[2]
                    if maxRadius > 1.0:
                        # draw the outer circle
                        cv2.circle(frame,(i[0],i[1]),maxRadius,(0,0,255),2)
                        # draw the center of the circle
                        cv2.circle(frame,(i[0],i[1]),1,(0,0,255),3)
                        xc = i[0]
                        yc = i[1] 
    if found: 
        print "ball detected at position:",xc, ",", yc, " with radius:", maxRadius
    else: 
        print "no ball" 
    cv2.imshow('frame',frame)
    cv2.imshow('edges',edges)
    k = cv2.waitKey(5) & 0xFF
    if k == 27:
        break
cv2.destroyAllWindows()
