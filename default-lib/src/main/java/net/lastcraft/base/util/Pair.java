package net.lastcraft.base.util;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Pair<A, B> {

    private A first;
    private B second;
}
