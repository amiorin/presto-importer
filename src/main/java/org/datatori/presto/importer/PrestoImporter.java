/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.datatori.presto.importer;

import java.util.Optional;
import java.util.stream.Stream;

import static io.airlift.airline.SingleCommand.singleCommand;

public final class PrestoImporter
{
    private PrestoImporter() {}

    public static void main(String[] args)
    {
        try {
            Console console = singleCommand(Console.class).parse(args);
            if (console.helpOption.showHelpIfRequested() ||
                    console.versionOption.showVersionIfRequested()) {
                return;
            }

            System.exit(console.run() ? 0 : 1);
        }
        catch (RuntimeException e) {
            Optional<Throwable> rootCause = Stream.iterate(e, Throwable::getCause)
                    .filter(element -> element.getCause() == null)
                    .findFirst();
            if (rootCause.isPresent()) {
                System.err.println(rootCause.get().getMessage());
            }
            else {
                throw e;
            }
            System.exit(1);
        }
    }
}
