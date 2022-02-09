# LocationUpdatesSample

Repo to reproduce strange scenario using FusedLocationProvider. 

1. Create LocationRequest using Interval(30 sec) and Fastest Interval (10 sec)
2. Start receiving location updates when app is in the foreground in the requested interval 
3. Background your application - stop receiving your location updates - I guess this is correct behaviour. 
4. Start Google Maps application or any app that is utilizing google map inside it. 
5. See that you start receiving location updates in your app extremely fast, like every one second. 

