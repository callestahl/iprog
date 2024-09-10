#include "Transmitter.hpp"

Transmitter::Transmitter() {}

void Transmitter::connectToWifi() {
  Serial.print("Connecting to ");
  Serial.println(ssid);
  // try to connect to wifi and wait until connected
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void Transmitter::sendData(float data) {
  // convert the float value into a byte array
  uint8_t byteArray[sizeof(float)];
  memcpy(byteArray, &data, sizeof(float));

  // begin UDP packet
  if (udp.beginPacket(receiverAddress, receiverPort) == 0) {
    Serial.println("Error: Could not begin UDP packet");
    return;
  }

  // write the data to the packet
  if (udp.write(byteArray, sizeof(byteArray)) != sizeof(byteArray)) {
    Serial.println("Error: Could not write UDP packet");
    return;
  }

  // send the packet
  if (udp.endPacket() == 0) {
    Serial.println("Error: Could not send UDP packet");
    return;
  }
}
