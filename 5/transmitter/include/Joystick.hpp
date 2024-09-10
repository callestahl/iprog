#pragma once

#include <Arduino.h>

/**
 * @class Joystick
 * @brief represents a joystick with x, y, and switch inputs.
 */
class Joystick {
 public:
  /**
   * @brief construct a new Joystick object
   *
   * @param pinX the analog pin connected to the x pin of the joystick
   * @param pinY the analog pin connected to the y pin of the joystick
   * @param pinSwitch the digital pin connected to the switch of the joystick
   */
  Joystick(uint8_t pinX, uint8_t pinY, uint8_t pinSwitch);
  /**
   * @brief read the joystick position as a voltage representing a note in
   * 1V/octave standard
   *
   * @return The voltage value as a float
   */
  float readVoltage();

  /**
   * @brief read the state of the joysticks switch
   *
   * @return true if the switch is pressed otherwise false
   */
  bool readSwitch();

 private:
  uint8_t octave = 4;  // what octave to play
  uint8_t pinX;
  uint8_t pinY;
  uint8_t pinSwitch;
};