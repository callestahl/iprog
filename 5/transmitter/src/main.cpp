#include <Arduino.h>

#include "Joystick.hpp"
#include "VibrationSensor.hpp"
#include "Transmitter.hpp"

// Pin numbers
constexpr uint8_t JOYSTICK_X_PIN = 34;
constexpr uint8_t JOYSTICK_Y_PIN = 35;
constexpr uint8_t JOYSTICK_SWITCH_PIN = 12;
constexpr uint8_t VIBRATION_SENSOR_PIN = 23;

Joystick joystick(JOYSTICK_X_PIN, JOYSTICK_Y_PIN, JOYSTICK_SWITCH_PIN);
VibrationSensor vibrationSensor(VIBRATION_SENSOR_PIN, 200);
Transmitter transmitter;

void setup() {
  pinMode(VIBRATION_SENSOR_PIN, INPUT_PULLDOWN);

  Serial.begin(9600);
  transmitter.connectToWifi();
}

void loop() {
  if (vibrationSensor.read()) {
    Serial.println(joystick.readVoltage());
    transmitter.sendData(joystick.readVoltage());
  }
}

