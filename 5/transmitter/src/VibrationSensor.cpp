#include "VibrationSensor.hpp"

VibrationSensor::VibrationSensor(uint8_t pin, uint16_t reTriggerDelay)
    : pin(pin), reTriggerDelay(reTriggerDelay), lastTrigger(0) {
  pinMode(pin, INPUT_PULLDOWN);
}

bool VibrationSensor::read() {
  uint32_t currentTime = millis();
  // only trigger if sensor is active and last trigger time was more than
  // reTriggerDelay ago
  if (digitalRead(pin) and (currentTime - lastTrigger) > reTriggerDelay) {
    lastTrigger = currentTime;
    return true;
  }
  return false;
}
