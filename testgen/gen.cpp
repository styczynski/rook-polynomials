#include <iostream>
#include <stdlib.h>
#include <time.h>
#include <fstream>
#include <sstream>
using namespace std;

string intToStr(int n) {
    std::ostringstream out;
    out << n;
    return out.str();
}

int main(void) {
  ios_base::sync_with_stdio(0);
  cin.tie(NULL);
  srand (time(NULL));

  for(int i=2;i<30;++i) {
    const int dim = rand() % (i/2+3) + i/4 + 2;
    const int pufts = rand() % (dim+dim/2+2) + dim+dim/3+1;
    int cnt = 0;
    const int chseed = rand() % 40 + 30;
    ofstream ofs((string("tests/test")+intToStr(i)+".in").c_str(), std::ofstream::out);
    ofs<<dim<<"\n";
    for(int y=0;y<dim;++y) {
      for(int x=0;x<dim;++x) {
        const int ch = rand()%100;
        if(ch>chseed) {
          ofs<<"#";
          ++cnt;
        } else {
          ofs<<".";
        }
      }
      ofs<<"\n";
    }
    ofs.close();
  }

  return 0;
}
