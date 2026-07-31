import 'package:flutter/material.dart';
import '../models/event.dart';

class EventPreviewScreen extends StatelessWidget {
  final List<EventModel> events;
  const EventPreviewScreen({super.key, required this.events});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Event Preview')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: ListView.builder(
          itemCount: events.length,
          itemBuilder: (_, index) {
            final event = events[index];
            return Card(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(event.title, style: Theme.of(context).textTheme.titleLarge),
                    const SizedBox(height: 8),
                    Text('Date: ${event.date}'),
                    Text('Time: ${event.time}'),
                    Text('Location: ${event.location ?? '—'}'),
                    const SizedBox(height: 12),
                    Row(
                      children: [
                        OutlinedButton(onPressed: () {}, child: const Text('Edit')),
                        const SizedBox(width: 12),
                        OutlinedButton(onPressed: () {}, child: const Text('Delete')),
                        const SizedBox(width: 12),
                        FilledButton(onPressed: () {}, child: const Text('Add to Calendar')),
                      ],
                    ),
                  ],
                ),
              ),
            );
          },
        ),
      ),
    );
  }
}
