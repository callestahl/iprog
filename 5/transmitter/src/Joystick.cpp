#include "Joystick.hpp"

#include <cmath>

Joystick::Joystick(uint8_t pinX, uint8_t pinY, uint8_t pinSwitch)
    : pinX(pinX), pinY(pinY), pinSwitch(pinSwitch) {
  pinMode(pinX, INPUT);
  pinMode(pinY, INPUT);
  pinMode(pinSwitch, INPUT);
}

float Joystick::readVoltage() {
  float x = (analogRead(pinX) - 2048.0f) / 2048.0f;
  float y = (analogRead(pinY) - 2048.0f) / 2048.0f;
  float angle = (atan2(y, x) * 180.0f / M_PI) + 180.0f;
  float voltagesCMajor[7] = {0.000f, 0.167f, 0.333f, 0.417f,
                             0.583f, 0.750f, 0.917f};
  uint8_t index = static_cast<int>(floor(
      angle / (360.0f / (sizeof(voltagesCMajor) / sizeof(voltagesCMajor[0])))));
  return voltagesCMajor[index] + octave;
}

bool Joystick::readSwitch() { return digitalRead(pinSwitch); }