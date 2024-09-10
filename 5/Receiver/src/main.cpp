#include <Arduino.h>
#include <SPI.h>

#include <limits>

#include "CVOut.hpp"
#include "MCP_DAC.h"
#include "Receiver.hpp"

// pin numbers
constexpr uint8_t PIN_DAC_CS = 5;
constexpr uint8_t PIN_TRIGGER = 33;

// how long is a trigger signal in miliseconds
constexpr uint8_t triggerLength = 20;

// object initializations
Receiver receiver;
SPIClass spi = SPIClass(VSPI);  // SPI is used to control the DAC
MCP4822 dac(&spi);
CVOut cv(&dac, 0, 10.0f);

// timestamp of when a trigger signal was activated last
uint32_t lastTriggerTime = std::numeric_limits<uint32_t>::max();

/**
 * @brief setup function
 *
 * this function is called once after boot. it sets up Serial for printing,
 * connects the receiver object to wifi, sets up SPI and the DAC and initializes
 * the trigger pin
 */
void setup() {
  Serial.begin(4800);
  receiver.connectToWifi();

  // activate SPI and DAC
  spi.begin();
  dac.begin(PIN_DAC_CS);

  // initialize the pin used to send trigger signals
  pinMode(PIN_TRIGGER, OUTPUT);
  digitalWrite(PIN_TRIGGER, LOW);
}

/**
 * @brief loop function
 *
 * this function reads the received data. if it is above 0, the CV object is
 * updated with the received data and the lastTriggerTime is updated. it then
 * sets the state of the trigger pin depending on when it last was triggered.
 */
void loop() {
  uint32_t currentTime = millis();
  // receive data
  float voltage = receiver.receiveData();
  // if received valid data, update CV and lastTriggerTime
  if (voltage >= 0.0f) {
    cv.setVoltage(voltage);
    lastTriggerTime = currentTime;
  }
  // set state of trigger pin depending on if enough time has passed since the
  // last trigger
  if (lastTriggerTime + triggerLength < currentTime) {
    digitalWrite(PIN_TRIGGER, LOW);
  } else {
    digitalWrite(PIN_TRIGGER, HIGH);
  }
}