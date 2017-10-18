#include <Servo.h>

#define sonic_send_pin 12
#define sonic_receive_pin 2
#define sonic_speed 340

#define value_send_delay 100
#define pulse_timeout 17000

#define pwm_top 2000
#define pwm_bottom 1000

#define steer_pin 9
#define throttle_pin 10

//Distance sensor
volatile double distance;
volatile unsigned long lastSonicMilli;
volatile unsigned long lastSonicMicro;
volatile unsigned long lastValueSentTime;


//Vehicle control
Servo throttle;
Servo steer;

int throttleValue;
int steerValue;
bool steerMode;

void setup() {
  //Serial setup
  Serial.begin(9600);

  //Vehicle control setup
  throttle.attach(steer_pin);
  steer.attach(throttle_pin);

  throttleValue = (pwm_top + pwm_bottom) / 2;
  steerValue = (pwm_top + pwm_bottom) / 2;
  steerMode = true;

  //Distance Sensor setup
  lastSonicMicro = 0;
  lastSonicMilli = 0;
  lastValueSentTime = 0;
  distance = 0;

  pinMode(sonic_send_pin, OUTPUT);
  pinMode(sonic_receive_pin, INPUT_PULLUP);

  attachInterrupt(digitalPinToInterrupt(sonic_receive_pin), sonicHit, RISING);
  sonicPulse();
}

//Main loop
void loop() {
  usbCarControl();
  sensorValue();
}

//Reads from serial port to get commands to stear the vehicle
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
        steerValue = map(value, -100, 100, pwm_bottom, pwm_top);
      } else {
        throttleValue = map(value, -100, 100, pwm_bottom, pwm_top);
      }
    }
  }

  throttle.write(throttleValue);
  steer.write(steerValue);
}

//Updates timer values for the last sonic pulse
void updateLastSonicPulse() {
  lastSonicMicro = micros();
  lastSonicMilli = millis();
}

//Updates currentDistance value
void sonicHit() {
  long sonicDeltaMicros = micros() - lastSonicMicro;
  distance = (sonic_speed / 2) * ((double)sonicDeltaMicros / 1000000);
  sonicPulse();
}

//Creates a sonic pulse
void sonicPulse() {
  digitalWrite(sonic_send_pin, LOW);
  delayMicroseconds(2);

  digitalWrite(sonic_send_pin, HIGH);
  delayMicroseconds(10);
  digitalWrite(sonic_send_pin, LOW);

  updateLastSonicPulse();
}

void sensorValue() {
  //If no pulse has been detected, send a new one
  if (lastSonicMicro > pulse_timeout) {
    sonicPulse();
  }

  //Send last sensor value every 100th milli second
  if (distance != 0 && millis() - lastValueSentTime >= value_send_delay) {
    Serial.println(distance);
    Serial.flush();
    lastValueSentTime = millis();
  }
}

