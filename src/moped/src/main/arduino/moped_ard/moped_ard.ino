#include <Servo.h>

Servo throttle;
Servo steer;

int TOP = 2000;
int BOTTOM = 1000;

int throttleValue;
int steerValue;
bool steerMode;

unsigned long pulseTime;
unsigned long lastSendTime;

void setup() {
  Serial.begin(9600);
  throttle.attach(9);
  steer.attach(10);

  throttleValue = 1500;
  steerValue = 1500;
  steerMode = true;

  pinMode(12, OUTPUT);
  pinMode(13, INPUT);

  lastSendTime = 0;
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
        steerValue = map(value, -100, 100, BOTTOM, TOP);
      } else {
        throttleValue = map(value, -100, 100, BOTTOM, TOP);
      }
    }
  }

  throttle.write(throttleValue);
  steer.write(steerValue);
}

void sensorValue() {
  digitalWrite(12, LOW);
  delayMicroseconds(2);

  digitalWrite(12, HIGH);
  delayMicroseconds(10);
  digitalWrite(12, LOW);

  //23200
  pulseTime = pulseIn(13, HIGH, 13200);

  double distance = (340 / 2) * ((double)pulseTime / 1000000);

  if (pulseTime != 0 && micros() - lastSendTime >= 100000) {
    lastSendTime = micros();
    Serial.println(distance);
    Serial.flush();
  }
}

