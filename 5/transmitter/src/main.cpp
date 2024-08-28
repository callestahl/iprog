#include <Arduino.h>

constexpr uint8_t PIN = 23;

void setup() { pinMode(PIN, OUTPUT); }

void loop() {
  digitalWrite(PIN, HIGH);
  sleep(1);
  digitalWrite(PIN, LOW);
  sleep(1);
}
