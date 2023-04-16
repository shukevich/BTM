package com.issoft.btm.util


import groovy.json.JsonSlurper

class JSONParser {

    private static def slurper = new JsonSlurper()

    static def findId(String responseBody) {
        return slurper.parseText(responseBody)["_links"]["self"]["href"].tokenize("/")[-1]
    }

    static def findIdEmbedded(String responseBody, String entityName) {
        return slurper.parseText(responseBody)["_embedded"]["${entityName}"][0]["_links"]["self"]["href"].tokenize("/")[-1]
    }

    static def readJSONFromFileResponse(String fileName) {
        def inputFile = new File("src/test/resources/integrationTests/responses/${fileName}.json")
        return new JsonSlurper().parseText(inputFile.text)
    }

    static def readJSONFromFileRequest(String fileName) {
        def inputFile = new File("src/test/resources/integrationTests/requests/${fileName}.json")
        return new JsonSlurper().parseText(inputFile.text)
    }

    static def parseJSONResponse(String response) {
        return new JsonSlurper().parseText(response)
    }
}
