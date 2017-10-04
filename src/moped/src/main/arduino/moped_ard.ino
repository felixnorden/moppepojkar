#include <Ultrasonic.h>
#include <Servo.h>

Servo throttle;
Servo steer;

int TOP = 2000;
int BOTTOM = 1000;

Ultrasonic ultrasonic(12, 13);
unsigned long lastSensorTime;
bool sensorEnabled;

void setup() {
  Serial.begin(9600);
  throttle.attach(9);
  steer.attach(10);

  sensorEnabled = false;
  lastSensorTime = millis();
}

void loop() {

  if (Serial.available() > 1) {
    int command = Serial.read();
    int value = Serial.read();
    value = value - 128;

    if (command == -128) {
      steer.write(map(value, -100, 100, BOTTOM, TOP));
    } else if (command == -127) {
      throttle.write(map(value, -100, 100, BOTTOM, TOP));
    }
  }

  long currentTime = millis();
  if (sensorEnabled && currentTime - lastSensorTime > 70) {
    int distance = ultrasonic.distanceRead();
    Serial.println(distance);
  }

}
