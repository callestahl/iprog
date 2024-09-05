#include "Transmitter.hpp"

Transmitter::Transmitter() {}

void Transmitter::connectToWifi() {
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting...");
  }
  Serial.println("Connected!");
}

void Transmitter::sendData(float data) {
    uint8_t byteArray[sizeof(float)];
    memcpy(byteArray, &data, sizeof(float));
    udp.beginPacket(receiverAddress, receiverPort);
    udp.write(data);
    udp.endPacket();
}
