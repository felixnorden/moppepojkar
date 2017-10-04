#include <Servo.h>

Servo throttle;
Servo steer;

int TOP = 2000;
int BOTTOM = 1000;
bool steerMode;

long pulseTime;

void setup() {
  Serial.begin(9600);
  throttle.attach(9);
  steer.attach(10);
  steerMode = true;

  pinMode(12, OUTPUT);
  pinMode(13, INPUT);
}

void loop() {
  usbCarControl();
  sensorValue();
}

void usbCarControl() {
  if (Serial.available() > 0) {
    int value = Serial.read();
    value = value - 128;

    if (value == -128) {
      steerMode = true;
    } else if (value == -127) {
      steerMode = false;
    } else if (value >= -100 && value <= 100) {
      if (steerMode) {
        steer.write(map(value, -100, 100, BOTTOM, TOP));
      } else {
        throttle.write(map(value, -100, 100, BOTTOM, TOP));
      }
    }
  }
}

void sensorValue() {
  digitalWrite(12, LOW);
  delayMicroseconds(2);

  digitalWrite(12, HIGH);
  delayMicroseconds(10);
  digitalWrite(12, LOW);

  pulseTime = pulseIn(13, HIGH, 23200);

  double distance = (340 / 2) * ((double)pulseTime / 1000000);

  if (pulseTime != 0) {
    Serial.println(distance);
    Serial.flush();
  }
}

