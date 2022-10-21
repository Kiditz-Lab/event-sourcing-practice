package com.stafsus.practice.product.command.rest

import org.axonframework.config.EventProcessingConfiguration
import org.axonframework.eventhandling.TrackingEventProcessor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/management")
class EventReplayController(
    private val configuration: EventProcessingConfiguration
) {

    @PostMapping("/processor/{name}/reset")
    fun replayEvents(@PathVariable name: String): ResponseEntity<String> {
        val processorOpt = configuration.eventProcessor(name, TrackingEventProcessor::class.java)
        if (processorOpt.isPresent) {
            val processor = processorOpt.get()
            processor.shutDown()
            processor.resetTokens()
            processor.start()
            return ResponseEntity.ok().body("Event processor with a name $name has been reset")
        }
        return ResponseEntity.ok().body("Failed to reset event")
    }
}