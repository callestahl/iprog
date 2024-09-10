#pragma once
#include <Arduino.h>

/**
 * @class VibrationSensor
 * @brief a class to represent a vibration sensor with a re-trigger delay
 */
class VibrationSensor {
 public:
  /**
   * @brief construct a new VibrationSensor object
   *
   * @param pin the pin connected to the vibration sensor
   * @param reTriggerDelay the delay in milliseconds to prevent multiple
   * triggers
   */
  VibrationSensor(uint8_t pin, uint16_t reTriggerDelay);

  /**
   * @brief read the state of the vibration sensor
   *
   * @return true if the sensor is triggered, otherwise false
   */
  bool read();

 private:
  uint8_t pin;
  uint16_t reTriggerDelay;
  uint32_t lastTrigger;  // timestamp of the last trigger
};
