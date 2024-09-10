#include "Joystick.hpp"

#include <cmath>

Joystick::Joystick(uint8_t pinX, uint8_t pinY, uint8_t pinSwitch)
    : pinX(pinX), pinY(pinY), pinSwitch(pinSwitch) {
  pinMode(pinX, INPUT);
  pinMode(pinY, INPUT);
  pinMode(pinSwitch, INPUT);
}

/**
 * @brief read the values from the joysticks x and y axes and calculate the
 * corresponding musical note voltage
 *
 * this function reads the values from the joysticks x and y pins,
 * calculates the angle of the joystick, and maps this angle to a musical note
 * voltage in the C major scale. the octave value is added to the result
 *
 * @return the voltage value corresponding to the joysticks position in the C
 * major scale according to 1V/oct
 */
float Joystick::readVoltage() {
  // turn analog values into values that can be used to calculate angle
  float x = (analogRead(pinX) - 2048.0f) / 2048.0f;
  float y = (analogRead(pinY) - 2048.0f) / 2048.0f;

  // turn coordinates into angle
  float angle = (atan2(y, x) * 180.0f / M_PI) + 180.0f;

  // voltages for C0 to B0
  float voltagesCMajor[7] = {0.000f, 0.167f, 0.333f, 0.417f,
                             0.583f, 0.750f, 0.917f};

  // calculate the index of the voltage based on the angle
  uint8_t index = static_cast<int>(floor(
      angle / (360.0f / (sizeof(voltagesCMajor) / sizeof(voltagesCMajor[0])))));
  // return the voltage at the calculated index
  return voltagesCMajor[index] + octave;
}

bool Joystick::readSwitch() { return digitalRead(pinSwitch); }