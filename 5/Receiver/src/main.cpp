#include <Arduino.h>
#include <SPI.h>

#include <limits>

#include "CVOut.hpp"
#include "MCP_DAC.h"
#include "Receiver.hpp"

constexpr uint8_t PIN_DAC_CS = 5;
constexpr uint8_t PIN_TRIGGER = 33;

constexpr uint8_t triggerLength = 20;

Receiver receiver;
SPIClass spi = SPIClass(VSPI);
MCP4822 dac(&spi);
CVOut cv(&dac, 0, 10.0f);
uint32_t lastTriggerTime = std::numeric_limits<uint32_t>::max();

void setup() {
  Serial.begin(4800);
  receiver.connectToWifi();

  spi.begin();
  dac.begin(PIN_DAC_CS);

  pinMode(PIN_TRIGGER, OUTPUT);
  digitalWrite(PIN_TRIGGER, LOW);
}

void loop() {
  uint32_t currentTime = millis();
  float voltage = receiver.receiveData();
  if (voltage >= 0.0f) {
    cv.setVoltage(voltage);
    lastTriggerTime = currentTime;
  }
  if (lastTriggerTime + triggerLength < currentTime) {
    digitalWrite(PIN_TRIGGER, LOW);
  } else {
    digitalWrite(PIN_TRIGGER, HIGH);
  }
}