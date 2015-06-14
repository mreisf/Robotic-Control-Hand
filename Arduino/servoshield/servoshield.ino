#include <SPI.h>
#include <Adb.h>
#include <Servo.h>
 

#define  SERVO1  4
#define  SERVO2  5
#define  SERVO3  6
#define  SERVO4  7
 
Servo servos[4];
//Servo servos[2];
 
Connection * connection;
 
// Elapsed time for ADC sampling
long lastTime;
 
// Event handler for shell connection; called whenever data sent from Android to Microcontroller
void adbEventHandler(Connection * connection, adb_eventType event, uint16_t length, uint8_t * data)
{
  // Data packets contain two bytes, first byte is pin number, second byte is state
  // For servos, in the range of [0..180], for LEDs, 0 or 1
  if (event == ADB_CONNECTION_RECEIVE)
  {
    int pin = data[0];
    switch (pin) {
    case 0x3:
       servos[0].write(data[1]);
      break;
    case 0x4:
       servos[1].write(data[1]);
      break;
    case 0x5:
      servos[2].write(data[1]);
      break;
    case 0x6:
      servos[3].write(data[1]);
      break;
    default:
      break;
    }
  }
}
 
void setup()
{
 
  // Init serial port for debugging
  Serial.begin(57600);
 
  // Attach servos
  servos[0].attach(SERVO1);
  servos[1].attach(SERVO2);
  servos[2].attach(SERVO3);
  servos[3].attach(SERVO4);
 
 
  // Init the ADB subsystem.  
  ADB::init();
 
  // Open an ADB stream to the phone's shell. Auto-reconnect. Use port number 4568
  connection = ADB::addConnection("tcp:4567", true, adbEventHandler);  
}
 
void loop()
{
  // Poll the ADB subsystem.
  ADB::poll();
}
 
