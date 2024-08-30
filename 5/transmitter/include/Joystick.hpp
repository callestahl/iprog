#pragma once

#include <Arduino.h>

class Joystick {
public:
    Joystick(uint8_t pinX, uint8_t pinY, uint8_t pinSwitch);
    float readVoltage();
    bool readSwitch();

private:
    uint8_t octave = 4;
    uint8_t pinX;
    uint8_t pinY;
    uint8_t pinSwitch;
};