
ALTER TABLE `mes_boutiques`
  ADD PRIMARY KEY (`id`);
  
ALTER TABLE `mes_boutiques_preferes`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `mes_boutiques`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
  
ALTER TABLE `mes_boutiques_preferes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
  