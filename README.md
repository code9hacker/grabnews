# grabnews
Demo news application using newsapi.org services

Screenshots
![Splash Screen](https://drive.google.com/file/d/1X2AslMxPxTBNQAsfYvobTQCj1hXqtaFv/view?usp=sharing)
![Country Selection](https://drive.google.com/file/d/1VwvBACIFH_QuIlCRiScuH3zuuF1szjb3/view?usp=sharing)
![Top Headlines](https://drive.google.com/file/d/1FH34Gkc012upy_G2uvpxxq8cxfCixtbH/view?usp=sharing)
![News Webview](https://drive.google.com/file/d/16u_aKsW_qx2AVxUBHXQuuUd4KAk4_hGV/view?usp=sharing)

Download apk : https://drive.google.com/file/d/17-AIYLY1AQ1V8RQToZ6KG4XHM3QNzq8_/view?usp=sharing

Some points about implementation :-
- Works offline. Saves response in self managed Http response cache manager.
- Employs self managed image caching on top of Glide
- Has a Network Helper layer written on top of OkHttp supporting Rx Observable streams and easy implementation
- Loads a config file on app launch which can be used to maintain some app level static configurations that can be remotely controlled.
- Singletons using DI implemented via Dagger2
- Databinding and MVVM
