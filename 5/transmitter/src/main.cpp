#include <Arduino.h>

#include "Joystick.hpp"
#include "Transmitter.hpp"
#include "VibrationSensor.hpp"

/**
 * @file main.cpp
 * @brief this file contains the main setup and loop functions for the program
 */

// pin numbers
constexpr uint8_t JOYSTICK_X_PIN = 34;
constexpr uint8_t JOYSTICK_Y_PIN = 35;
constexpr uint8_t JOYSTICK_SWITCH_PIN = 12;
constexpr uint8_t VIBRATION_SENSOR_PIN = 23;

// object initialization
Joystick joystick(JOYSTICK_X_PIN, JOYSTICK_Y_PIN, JOYSTICK_SWITCH_PIN);
VibrationSensor vibrationSensor(VIBRATION_SENSOR_PIN, 200);
Transmitter transmitter;

/**
 * @brief setup function
 *
 * called once after boot. sets up serial for printing and connects transmitter
 * to wifi
 */
void setup() {
  Serial.begin(9600);
  transmitter.connectToWifi();
}

/**
 * @brief loop function
 *
 * this function is called repeatedly in a loop after the setup function. it
 * reads the vibration sensor and if it is active it reads data from the
 * joystick and sends it with the transmitter object
 */
void loop() {
  if (vibrationSensor.read()) {
    Serial.println(joystick.readVoltage());
    transmitter.sendData(joystick.readVoltage());
  }
}
