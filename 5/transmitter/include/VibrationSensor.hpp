#pragma once
#include <Arduino.h>

class VibrationSensor {
 public:
  VibrationSensor(uint8_t pin, uint16_t reTriggerDelay);
  bool read();

 private:
  uint8_t pin;
  uint16_t reTriggerDelay;
  uint32_t lastTrigger;
};
