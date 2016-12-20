@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
    
import groovyx.net.http.HTTPBuilder
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder
import static groovyx.net.http.Method.POST
import static groovyx.net.http.ContentType.TEXT

def http = new HTTPBuilder()

def systemList = new ArrayList<String>()


for(int i = 0; i < 1000; i++) {
    http.request( 'http://localhost:21088/dmacs/httprequests', POST, TEXT ) { req ->
        headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
        headers.Accept = 'application/json'
     
        def builder = new groovy.json.JsonBuilder()
        builder.URL {'http://xxx.com:8090/controller/#/location=APP_EVENTSTREAM_LIST&timeRange=last_15_minutes.BEFORE_NOW.-1.-1.15&application=42'}
        //builder.URL {'http://www.google.com'}
        builder.deepLink {'http://xxx.net:8090/#location=APP_EVENT_VIEWER_MODAL&eventSummary=3827'}
        builder.Application {'Sample Application Name'}
        builder.Tier {'Sample Tier'}
        builder.Message {'Sample Event Message'}
        builder.Severity {'WARN'}
        builder.EventType {'POLICY_OPEN_WARNING'}
        builder.EventTime {'Tue May 17 16:41:30 GMT 2016'}
        builder.PolicyName {'Sample Policy Name'}
           
        body = builder.toString()
            
        def stringResponse = ''
        def result
        response.success = { resp, reader ->
            assert resp.statusLine.statusCode == 200
            println "Got response: ${resp.statusLine}"
            println "Content-Type: ${resp.headers.'Content-Type'}"
            //println reader.text
            
        }
            
        response.'404' = {
            println 'Not found'
        }
    }
    sleep(60000)
}
