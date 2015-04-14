#include <Poco/Net/HTTPClientSession.h>
#include <Poco/Net/HTTPRequest.h>
#include <Poco/Net/HTTPResponse.h>
#include <Poco/StreamCopier.h>
#include <Poco/Path.h>
#include <Poco/URI.h>
#include <Poco/Exception.h>
#include <Poco/ThreadPool.h>
#include <Poco/Runnable.h>
#include <iostream>
#include <string>
#include <chrono>

using Poco::Net::HTTPClientSession;
using Poco::Net::HTTPRequest;
using Poco::Net::HTTPResponse;
using Poco::Net::HTTPMessage;
using Poco::StreamCopier;
using Poco::Path;
using Poco::URI;
using Poco::Exception;
using Poco::ThreadPool;
using Poco::Runnable;
using std::istream;
using std::cout;
using std::cerr;
using std::endl;
using std::string;
using std::chrono::system_clock;
using std::chrono::milliseconds;
using std::chrono::duration_cast;

const long N = 4;
const long M = 10000;
const URI uri("http://127.0.0.1:9090/");

class Work: public Runnable {
public:
	virtual void run()
	{
	  for(int i = 0; i < M; i++)
	  {
		  try
		  {
			HTTPClientSession session(uri.getHost(), uri.getPort());

			string path(uri.getPathAndQuery());
			if (path.empty()) path = "/";

			HTTPRequest req(HTTPRequest::HTTP_GET, path, HTTPMessage::HTTP_1_1);
			session.sendRequest(req);

			HTTPResponse res;
			//~ cout << res.getStatus() << " " << res.getReason() << endl;

			istream &is = session.receiveResponse(res);
			//~ StreamCopier::copyStream(is, cout);
		  }
		  catch (Exception &ex)
		  {
			cerr << ex.displayText() << endl;
		  }
	  }
	}
};

int main(int argc, char **argv)
{    
    auto start = system_clock::now();
    
    ThreadPool pool(N);
    Work work;
    
    for(int i = 0; i < N; i++)
    {
        pool.start(work);
    }
    
    pool.joinAll();
    
    auto total = system_clock::now() - start;
    
    cerr << "Throughput: " << (1000*M*N)/(duration_cast<milliseconds>(total).count()) << " req/s" << endl;
    
    return 0;
}
