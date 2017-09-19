package com_io;

 class CommunicatorFactoryImpl implements CommunicatorFactory {

    CommunicationsMediator comInstance;

     @Override
     public CommunicationsMediator getComInstance() {
         return comInstance;
     }
 }
