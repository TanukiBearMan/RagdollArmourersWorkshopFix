package tanukibear.ragdollawfix.mixin;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MixinPackageLayoutTest {

    @Test
    void mixinPackageContainsOnlyTopLevelMixinClasses() throws Exception {
        var mixinPackage = Path.of(System.getProperty("user.dir"))
                .resolve("build/classes/java/main/tanukibear/ragdollawfix/mixin");

        assertTrue(Files.isDirectory(mixinPackage), "compiled mixin package is missing");
        try (var stream = Files.list(mixinPackage)) {
            var classFiles = stream
                    .filter(path -> path.getFileName().toString().endsWith(".class"))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toSet());

            assertEquals(Set.of(
                    "SkinPartElementMixin.class",
                    "RagdollPartBlockEntityRendererMixin.class"
            ), classFiles);
        }
    }
}
