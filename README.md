# grabnews
Demo news application using newsapi.org services

Download apk : https://drive.google.com/file/d/17-AIYLY1AQ1V8RQToZ6KG4XHM3QNzq8_/view?usp=sharing

Some points about implementation :-
- Works offline. Saves response in self managed Http response cache manager.
- Employs self managed image caching on top of Glide
- Has a Network Helper layer written on top of OkHttp supporting Rx Observable streams and easy implementation
- Loads a config file on app launch which can be used to maintain some app level static configurations that can be remotely controlled.
- Singletons using DI implemented via Dagger2
- Databinding and MVVM
