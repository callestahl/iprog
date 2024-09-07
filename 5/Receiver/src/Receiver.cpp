#include "Receiver.hpp"

void Receiver::connectToWifi() {
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  udp.begin(port);
  Serial.print("Listening on UDP port ");
  Serial.println(port);
}

float Receiver::receiveData() {
  byte incomingPacket[255];

  int packetSize = udp.parsePacket();
  if (packetSize) {
    int length = udp.read(incomingPacket, 255);
    if (length > 0) {
      if (length == sizeof(float)) {
        float receivedValue;
        memcpy(&receivedValue, incomingPacket, sizeof(float));
        return receivedValue;
      }
    }
  }
  return -1.0f;
}
