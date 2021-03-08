# MobileDev
Alexis BOURDIN IOS 1

how you ensure user is the right one starting the app/How do you securely save user's data on your phone :
Only one user can be registered on the app so there shouldn't be any confusion possible.
(I tried adding .setUserAuthenticationRequired(true) in my key generator function to require phone password or fingerprint authentication but was getting errors)
Only the first user gets the possibity to register, after that only the login option is presented.
It's a password protected app, the password is stored in an EncryptedSharedPreferences file and is also encrypted using AES.
The keys are stored in the android keystore.


How did you hide the API url :
c++ code can’t be decompiled, it can be only disassembled.
That's why I stored the url in a C++ class and I fetch the string by function calling from my Kotlin classes.
This was possible using NDK and CMake
While it's not perfect security it makes retrieving the url much harder.
The url can't be retrieved during connexion as the app uses TLS it's encrypted.
The app trust only the CA for the api url and won't communicate with any other source.

Additionnaly the code is obfuscated and shrinked to limit the possibilities of reverse engineering.
Internet is the only permission required for the app to function.
